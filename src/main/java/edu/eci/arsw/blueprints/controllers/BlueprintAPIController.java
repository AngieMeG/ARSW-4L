/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

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

    /**
     * Method that allows the user to make a GET request to the blueprints in persistence 
     * @return the reponse to the GET request in JSON format
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllBlueprints(){   
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(bps.getAllBlueprints(),HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error loading the blueprints.",HttpStatus.NOT_FOUND);
        }        
    }
    
    /**
     * Method that allows the user to make a GET request to the authoe's bluepints in persistence
     * @param author, the desired author of the bluepints
     * @return the reponse to the GET request in JSON format
     */
    @RequestMapping(value = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsForAuthor(@PathVariable String author){   
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(bps.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error loading " + author + "'s blueprints.",HttpStatus.NOT_FOUND);
        }        
    }

    /**
     * Method that allows the user to make a GET request to one blueprint by an author
     * @param author, the desired author of the bluepint
     * @param bpname, the blueprint's name to consult
     * @return the reponse to the GET request in JSON format
     */
    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprint(@PathVariable String author, @PathVariable String bpname){   
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(bps.getBlueprint(author,bpname),HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error loading " + author + "'s " + bpname + " blueprint",HttpStatus.NOT_FOUND);
        }        
    }

    /**
     * Method that allows the user to make a POST request to save one blueprint on persistence
     * @param bp, the desired blueprint
     * @return the reponse to the POST
     */
    @RequestMapping(method = RequestMethod.POST)	
    public ResponseEntity<?> saveBlueprint(@RequestBody Blueprint bp){
        try {
            System.out.println(bp);
            bps.saveBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error making the POST request to " + bp.getAuthor() + "'s  blueprint by name " + bp.getName(),HttpStatus.FORBIDDEN);            
        }        
    }

    /**
     * Method that allows the user to make a POST request to save one blueprint on persistence
     * @param author, the author of the blueprint to modify
     * @param bprintname, the name of the blueprint to modify
     * @param bp, the blueprint to modify
     * @return the reponse to the PUT
     */
    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> editBlueprint(@PathVariable String author, @PathVariable String bpname, @RequestBody Blueprint bp){   
        try {
            //obtener datos que se enviarán a través del API
            bps.editBlueprint(author,bpname, bp);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException | BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error editing " + author + "'s blueprint by name " + bpname ,HttpStatus.NOT_FOUND);
        }        
    } 
}


