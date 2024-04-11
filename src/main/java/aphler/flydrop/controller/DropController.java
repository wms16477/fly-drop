package aphler.flydrop.controller;

import aphler.common.response.R;
import aphler.flydrop.dto.TextDropDto;
import aphler.flydrop.po.DropObj;
import aphler.flydrop.service.DropObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DropController {

    @Autowired
    private DropObjService dropObjService;

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
