package aphler.flydrop.service.impl;

import aphler.common.util.FileUtils;
import aphler.flydrop.dto.TextDropDto;
import aphler.flydrop.enums.DropType;
import aphler.ossClient.client.OSSClient;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import aphler.flydrop.po.DropObj;
import aphler.flydrop.service.DropObjService;
import aphler.flydrop.mapper.DropObjMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author aphler
 * @description 针对表【drop_obj(传输对象)】的数据库操作Service实现
 * @createDate 2024-04-09 17:41:54
 */
@Slf4j
@Service
public class DropObjServiceImpl extends ServiceImpl<DropObjMapper, DropObj> implements DropObjService {

    private static final int codeLen = 4;

    private static final String oss_prefix = "FlyDrop";

    @Autowired
    private OSSClient ossClient;


    @Override
    public String dropText(TextDropDto dto) {
        log.info("接收到文本， 参数: {}", dto);
        if (!StringUtils.hasLength(dto.getText()) || dto.getExpiresMinute() == null) {
            throw new RuntimeException("参数错误!");
        }
        String code = getCode();
        DropObj dropObj = new DropObj();
        dropObj.setCode(code);
        dropObj.setContent(dto.getText());
        dropObj.setType(DropType.TEXT.typeKey);
        //设置过期时间
        dropObj.setExpiresTime(DateUtil.offset(new Date(), DateField.MINUTE, dto.getExpiresMinute()));
        save(dropObj);
        return code;
    }

    @Override
    public String dropFile(MultipartFile file, Integer expiresMinute) {
        if (file == null || expiresMinute == null) {
            throw new RuntimeException("参数错误!");
        }
        log.info("上传文件, 文件名: {}, 文件大小: {}", file.getOriginalFilename(), file.getSize());
        //获取code
        String code = getCode();
        try {
            String originalFilename = StringUtils.hasLength(file.getOriginalFilename()) ? file.getOriginalFilename() : "file";
            String fileSuffixName = FileUtils.getFileSuffixName(originalFilename);
            String ossFileName = UUID.randomUUID() + (StringUtils.hasLength(fileSuffixName) ? "." + fileSuffixName : "");
            String link = ossClient.upload(file.getInputStream(), oss_prefix + "/" + ossFileName, originalFilename);
            log.info("上传文件成功, 文件名: {}", originalFilename);
            DropObj dropObj = new DropObj();
            dropObj.setType(DropType.FILE.typeKey);
            dropObj.setOssAddr(link);
            dropObj.setCode(code);
            dropObj.setFileName(originalFilename);
            //设置过期时间
            dropObj.setExpiresTime(DateUtil.offset(new Date(), DateField.MINUTE, expiresMinute));
            save(dropObj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return code;
    }

    @Override
    public DropObj getDropObj(String code) {
        LambdaQueryWrapper<DropObj> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DropObj::getCode, code);
        DropObj one = this.getOne(queryWrapper);
        if (one == null) {
            throw new RuntimeException("取件码不存在!");
        }
        if (DateUtil.compare(new Date(), one.getExpiresTime()) > 0) {
            //过期-删除
            this.removeById(one.getId());
            throw new RuntimeException("取件码不存在!");
        }
        return one;
    }

    @Override
    //清理过期对象
    public int removeExpiresDropObj() {
        LambdaQueryWrapper<DropObj> queryWrapper = Wrappers.lambdaQuery(DropObj.class);
        queryWrapper.lt(DropObj::getExpiresTime, new Date());
        return baseMapper.delete(queryWrapper);
    }

    //取件码校验
    public boolean existCode(String code) {
        LambdaQueryWrapper<DropObj> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DropObj::getCode, code);
        return this.count(queryWrapper) > 0;
    }

    //获取取件码
    public String getCode() {
        String code;
        do {
            code = RandomUtil.randomNumbers(codeLen);
        } while (existCode(code));
        return code;
    }


}




