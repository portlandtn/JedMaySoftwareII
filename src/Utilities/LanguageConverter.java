/*
 * Copyright (C) 2019 Jedidiah May.
 *
 * This program is free software and part of an academic course of study
 * with Western Governor's University. This program is stored on my
 * personal git accounts so that I can collaborate from multiple computers
 * easily. If you find this, feel free to use it for general concept and
 * as a guidepost for your own coursework.
 *
 * This program is distributed in the hope that it will be useful,
 * but please do not copy any of the code verbatim without first
 * understanding how it works. If you're a student, I wish you the best
 * and hope this is of value to you. If you're not a student, I hope you
 * enjoy irregardless.
 *
 * Look for other projects on my github account at <https://github.com/portlandtn/>.
 */
package Utilities;

/**
 *
 * @author Jedidiah May
 */
public class LanguageConverter {
    
    // <editor-fold defaultstate="collapsed" desc="Enum Constant translations">
    public enum usernameNotFound {
        ENGLISH("The username does not exist."),
        SPANISH("El nombre de usuario no existe."),
        FRENCH("Le nom d'utilisateur n'existe pas."),
        GERMAN("Dieser Nutzername existiert nicht.");

        private String message;

        usernameNotFound(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }

    public enum usernamePasswordDoNotMatch {
        ENGLISH("The username and password do match any records."),
        SPANISH("El nombre de usuario y la contraseña coinciden con cualquier registro."),
        FRENCH("Le nom d'utilisateur et le mot de passe correspondent à tous les enregistrements."),
        GERMAN("Der Benutzername und das Passwort stimmen mit allen Datensätzen überein.");

        private String message;

        usernamePasswordDoNotMatch(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }

    public enum usernameInactive {
        ENGLISH("While this user does exist, their account has been made inactive. Please have the administrator update this account to use it for logging in."),
        SPANISH("Si bien este usuario existe, su cuenta se ha desactivado. Haga que el administrador actualice esta cuenta para usarla para iniciar sesión."),
        FRENCH("Bien que cet utilisateur existe, son compte est devenu inactif. Veuillez demander à l'administrateur de mettre à jour ce compte pour l'utiliser pour la connexion."),
        GERMAN("Dieser Benutzer ist zwar vorhanden, sein Konto wurde jedoch deaktiviert. Bitten Sie den Administrator, dieses Konto zu aktualisieren, um es für die Anmeldung zu verwenden.");

        private String message;

        usernameInactive(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }
    
    public enum usernameAndPasswordCannotBeEmpty {
        ENGLISH("The username and password fields cannot be empty."),
        SPANISH("Los campos de nombre de usuario y contraseña no pueden estar vacíos."),
        FRENCH("Les champs nom d'utilisateur et mot de passe ne peuvent pas être vides."),
        GERMAN("Die Felder Benutzername und Passwort dürfen nicht leer sein.");

        private String message;

        usernameAndPasswordCannotBeEmpty(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }

    public enum loginButton {
        ENGLISH("Login"),
        SPANISH("Iniciar sesión"),
        FRENCH("S'identifier"),
        GERMAN("Einloggen");

        private String message;

        loginButton(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }

    public enum pleaseLogInToContinueLabel {
        ENGLISH("Please Log In To Continue"),
        SPANISH("Por favor inicie sesión para continuar"),
        FRENCH("Merci de vous connecter pour continuer"),
        GERMAN("Bitte einloggen zum Fortfahren");

        private String message;

        pleaseLogInToContinueLabel(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }

    public enum schedulerLabel {
        ENGLISH("Jed May Scheduler"),
        SPANISH("Jed May Programador"),
        FRENCH("Jed May Planificateur"),
        GERMAN("Jed May Planer");

        private String message;

        schedulerLabel(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }

    public enum usernameHintText {
        ENGLISH("username"),
        SPANISH("nombre de usuario"),
        FRENCH("Nom d'utilisateur"),
        GERMAN("Nutzername");

        private String message;

        usernameHintText(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }
    
    public enum passwordHintText {
        ENGLISH("password"),
        SPANISH("contraseña"),
        FRENCH("mot de passe"),
        GERMAN("Passwort");

        private String message;

        passwordHintText(String message) {
            this.message = message;
        }

        public String getText() {
            return this.message;
        }
    }
    // </editor-fold>
    
    public static String translateUserNameNotFound() {
        switch (DataProvider.getLanguage()) {

            case "English":
                return usernameNotFound.ENGLISH.getText();
            case "French":
                return usernameNotFound.FRENCH.getText();
            case "Spanish":
                return usernameNotFound.SPANISH.getText();
            case "German":
                return usernameNotFound.GERMAN.getText();
            default:
                return usernameNotFound.ENGLISH.getText();
        }
    }
    
    public static String translateUserNameAndPasswordAreInvalid() {
        switch (DataProvider.getLanguage()) {

            case "English":
                return usernamePasswordDoNotMatch.ENGLISH.getText();
            case "French":
                return usernamePasswordDoNotMatch.FRENCH.getText();
            case "Spanish":
                return usernamePasswordDoNotMatch.SPANISH.getText();
            case "German":
                return usernamePasswordDoNotMatch.GERMAN.getText();
            default:
                return usernamePasswordDoNotMatch.ENGLISH.getText();

        }
    }
    
    public static String translateUserNameInactive() {
        switch (DataProvider.getLanguage()) {

            case "English":
                return usernameInactive.ENGLISH.getText();
            case "French":
                return usernameInactive.FRENCH.getText();
            case "Spanish":
                return usernameInactive.SPANISH.getText();
            case "German":
                return usernameInactive.GERMAN.getText();
            default:
                return usernameInactive.ENGLISH.getText();

        }
    }
    
    public static String translateUserNameAndPasswordCannotBeEmpty() {
        switch (DataProvider.getLanguage()) {

            case "English":
                return usernameAndPasswordCannotBeEmpty.ENGLISH.getText();
            case "French":
                return usernameAndPasswordCannotBeEmpty.FRENCH.getText();
            case "Spanish":
                return usernameAndPasswordCannotBeEmpty.SPANISH.getText();
            case "German":
                return usernameAndPasswordCannotBeEmpty.GERMAN.getText();
            default:
                return usernameAndPasswordCannotBeEmpty.ENGLISH.getText();

        }
    }
    
}
