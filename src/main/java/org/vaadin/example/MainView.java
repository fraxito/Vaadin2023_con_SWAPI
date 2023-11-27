package org.vaadin.example;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed
     *            bean.
     */
    public MainView(@Autowired GreetService service) {
        HorizontalLayout entradas = new HorizontalLayout();
        VerticalLayout resultados = new VerticalLayout();

        ComboBox<String> comboBox = new ComboBox<>("Browser");
        comboBox.setAllowCustomValue(false); //este deja que el usuario escriba lo que quiera en la caja del comboBox. Si se pone a false no deja
        comboBox.setItems("people", "planets", "starships");
        comboBox.setHelperText("Selecciona el tipo de petición");

        // Use TextField for standard text input

        TextField pideId = new TextField("Id de consulta");
        pideId.addClassName("bordered");
        entradas.add(comboBox, pideId);
        // Button click listeners can be defined as lambda expressions
        Button boton1 = new Button("Say hello", e -> {
            String tipo = comboBox.getValue();
            int id = Integer.parseInt(pideId.getValue());
            try {
                System.out.println(service.getSWAPI(tipo, id));
            } catch (Exception ex) {
            }
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button has a more prominent look.
        boton1.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        boton1.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.
        addClassName("centered-content");

        add(entradas, boton1);
    }

}
