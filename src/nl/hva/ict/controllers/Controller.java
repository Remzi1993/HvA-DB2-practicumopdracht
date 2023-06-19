package nl.hva.ict.controllers;

import nl.hva.ict.views.View;

/**
 * Abstracte class controller
 * @author HvA FDMCI HBO-ICT
 */
public abstract class Controller {

    // Zorg dat iedere class de methode getView heeft
    public abstract View getView();
}