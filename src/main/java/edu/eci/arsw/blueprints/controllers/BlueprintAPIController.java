/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import edu.eci.arsw.blueprints.model.*;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprint")
public class BlueprintAPIController {
    
    @Autowired
    BlueprintsServices bps = null;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllBlueprints(){   
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<Set<Blueprint>>(bps.getAllBlueprints(),HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<String>("Error loading blueprints",HttpStatus.NOT_FOUND);
        }        
    }
    
    @RequestMapping(value = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsForAuthor(@PathVariable String author){   
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<Set<Blueprint>>(bps.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<String>("Error loading blueprints",HttpStatus.NOT_FOUND);
        }        
    }

    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname){   
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<Blueprint>(bps.getBlueprint(author,bpname),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<String>("Error loading blueprints",HttpStatus.NOT_FOUND);
        }        
    }

    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.POST)	
    public ResponseEntity<?> saveBlueprint(@RequestBody Blueprint bp){
        try {
            System.out.println(bp);
            bps.saveBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error",HttpStatus.FORBIDDEN);            
        }        
    }

    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> editBlueprint(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint bp){   
        try {
            //obtener datos que se enviarán a través del API
            bps.editBlueprint(author, bpname, bp);
            return new ResponseEntity<Blueprint>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<String>("Error editing the blueprint",HttpStatus.NOT_FOUND);
        }        
    }
}

    
