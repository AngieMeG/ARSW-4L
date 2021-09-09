/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * @author hcadavid
 */
public interface BlueprintsPersistence {
    
    /**
     * Add a new blueprint to make persistence
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;
    
    /**
     * @param author blueprint's author
     * @param bprintname blueprint's name
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException;

    /**
     * @return All the blueprints
     */
    public Set<Blueprint> getAllBlueprints();

    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;

    /**
     * Edit the given blueprint values
     * @param author, the author of the blueprint to modify
     * @param bprintname, the name of the blueprint to modify
     * @param bp, the blueprint to modify
     * @throws BlueprintNotFoundException, if the given blueprint doesn't exist
     * @throws BlueprintPersistenceException
     */
    public void editBlueprint(String author, String bprintname, Blueprint bp) throws BlueprintNotFoundException, BlueprintPersistenceException;
}
