/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import edu.eci.arsw.blueprints.model.*;
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
    public ResponseEntity<Set<Blueprint>> getAllBlueprints(){   
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<Set<Blueprint>>(bps.getAllBlueprints(),HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<Set<Blueprint>>(new LinkedHashSet<Blueprint>(),HttpStatus.NOT_FOUND);
        }        
    }
    
}

    
