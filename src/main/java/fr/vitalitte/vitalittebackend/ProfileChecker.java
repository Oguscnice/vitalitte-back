package fr.vitalitte.vitalittebackend;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

public class ProfileChecker {

    private final Environment environment;

    public ProfileChecker (Environment environment) {
        this.environment = environment;
    }

    /**
     * Méthode écoutant l'événement de démarrage de l'application. Une fois l'application prête,
     * elle affiche le profil actif dans la console avec une couleur spécifique selon l'environnement.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void displayActiveProfile() {
        String textColor = "";
        String restetColor = "\33[0m"; // Blanc"
        String activeProfile = String.join(", ", environment.getActiveProfiles());

        if (activeProfile.equals("production")) {
            textColor = "\033[32m"; // Vert
        }

        if (activeProfile.equals("staging")) {
            textColor = "\033[36m"; // Cyan
        }

        if (activeProfile.equals("development") || activeProfile.equals("local")) {
            textColor = "\033[33m"; // Jaune
        }

        System.out.println(textColor + "Profile actif : " + activeProfile + restetColor);
    }
}
