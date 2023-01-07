package ru.annikonenkov.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Transactional(readOnly = true)
@RestController
@RequestMapping("/forTry")
public class ForTryController {

    @GetMapping("/get")
    public String getInfo(@RequestParam(value = "info") String info) {
        return String.format("This is the GET request. It has next requestParameter with info = %s", info);
    }

    @Transactional(readOnly = false)
    @PostMapping("/post")
    public String postInfo(@RequestParam(value = "info") String info) {
        return String.format("This is the POST request. It has next requestParameter with info = %s", info);
    }

    @Transactional()//by default is false
    // @PostMapping("/tryPostWithManyFiles")
    @RequestMapping(value = "/tryPostWithManyFiles", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"})
    @ResponseBody
    public String tryPostWithManyFiles(@RequestPart("file") List<MultipartFile> files, @RequestPart("Fila") MultipartFile fila) {
        String res = "";
        for (MultipartFile f : files) {
            String getOriginalFilename = f.getOriginalFilename();
            Long length = f.getSize();
            String name = f.getName();
            String resultName = String.format("getName() = %s, getSize() = %d, getOriginalFilename() = %s\n", name, length, getOriginalFilename);
            res += resultName;
        }

        String resultName = String.format("getName() = %s, getSize() = %d, getOriginalFilename() = %s\n", fila.getName(), fila.getSize(), fila.getOriginalFilename());

        res += resultName;
        return res;
    }

}
