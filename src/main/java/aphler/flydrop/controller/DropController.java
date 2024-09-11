package aphler.flydrop.controller;

import aphler.common.response.R;
import aphler.flydrop.dto.TextDropDto;
import aphler.flydrop.po.DropObj;
import aphler.flydrop.service.DropObjService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class DropController {

    @Autowired
    private DropObjService dropObjService;

    @GetMapping("getPublicDropObj")
    public R<Page<DropObj>> getPublicDropObj(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return R.success(dropObjService.getPublicDropObj(pageSize, pageNum));
    }

    @PostMapping("dropText")
    public R<String> dropText(@RequestBody TextDropDto dropDto) {
        return R.success(dropObjService.dropText(dropDto));
    }

    @PostMapping("dropFile")
    public R<String> dropFile(MultipartFile file, Integer expiresMinute) {
        return R.success(dropObjService.dropFile(file, expiresMinute));
    }

    @GetMapping("dropObj")
    public R<DropObj> dropObj(String code) {
        return R.success(dropObjService.getDropObj(code));
    }


}
