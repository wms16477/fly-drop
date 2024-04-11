package aphler.flydrop.service;

import aphler.flydrop.dto.TextDropDto;
import aphler.flydrop.po.DropObj;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author aphler
 * @description 针对表【drop_obj(传输对象)】的数据库操作Service
 * @createDate 2024-04-09 17:41:54
 */
public interface DropObjService extends IService<DropObj> {

    String dropText(TextDropDto dto);

    String dropFile(MultipartFile file, Integer expiresMinute);

    DropObj getDropObj(String code);

    //清理过期对象
    int removeExpiresDropObj();

}
