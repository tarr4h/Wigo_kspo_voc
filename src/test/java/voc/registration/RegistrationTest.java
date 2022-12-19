package voc.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.kspo.voc.kspo.process.service.VocRegistrationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistrationTest {

    VocRegistrationService service = new VocRegistrationService();


    @Test
    public void insertValidtaion(){
        Map<String, Object> param = new HashMap<>();

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> param1 = new HashMap<>();
        param1.put("value", "");
        param1.put("name", "title");
        list.add(param1);

        param.put("formArr", list);
        log.debug("param = {}", param);

        boolean bool = service.insertValidation(param);

        log.debug("bool = {}", bool);
    }
}
