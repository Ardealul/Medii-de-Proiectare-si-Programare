package services;

import model.Cursa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CursaRepository;
import repository.RepositoryException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/races")
public class CursaController {

    private static final String template = "Hello, %s!";

//    @Autowired
//    private CursaRepository cursaRepository;

    private ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
    private CursaRepository cursaRepository = (CursaRepository) factory.getBean("cursaRepository");

    @RequestMapping(method = RequestMethod.GET)
    public Cursa[] getAll(){
        List<Cursa> listaCursa = new ArrayList<>();
        cursaRepository.findAll().forEach(listaCursa::add);
        return listaCursa.toArray(new Cursa[listaCursa.size()]);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cursa save(@RequestBody Cursa cursa){
        cursaRepository.save(cursa);
        return cursa;
    }

    @RequestMapping(value = "/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return String.format(template, name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        Cursa cursa = cursaRepository.findOne(id);
        if(cursa == null)
            return new ResponseEntity<String>("Race not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Cursa>(cursa, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Cursa update(@RequestBody Cursa cursa, @PathVariable String id){
        cursaRepository.update(id, cursa);
        return cursa;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            cursaRepository.delete(id);
            return new ResponseEntity<Cursa>(HttpStatus.OK);
        }
        catch (RepositoryException ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String raceError(RepositoryException ex){
        return ex.getMessage();
    }
}
