package aphler.flydrop.handler;

import aphler.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public R handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        e.printStackTrace();
        return R.error("数据过长!");
    }

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public R handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return R.error(e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler({Exception.class})
    public R handleException(Exception e) {
        e.printStackTrace();
        return R.error("未知异常");
    }

}
