package org.vaadin.example;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
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
        VerticalLayout pagina = new VerticalLayout();
        HorizontalLayout entradas = new HorizontalLayout();
        VerticalLayout resultados = new VerticalLayout();
        VerticalLayout resultadosGrid = new VerticalLayout();

        pagina.add(entradas, resultadosGrid);
        ComboBox<String> comboBox = new ComboBox<>("Browser");
        comboBox.setAllowCustomValue(false); //este deja que el usuario escriba lo que quiera en la caja del comboBox. Si se pone a false no deja
        comboBox.setItems("people", "planets", "starships");
        comboBox.setHelperText("Selecciona el tipo de petición");

        Grid<CaracterSW> grid = new Grid<>(CaracterSW.class, false);
        grid.addColumn(CaracterSW::getName).setHeader("NOMBRE");
        grid.addColumn(CaracterSW::getHeight).setHeader("ALTURA");
        grid.addColumn(CaracterSW::getMass).setHeader("PESO");
        grid.addColumn(CaracterSW::getHair_color).setHeader("COLOR DE PELO");
        grid.addColumn(CaracterSW::getEye_color).setHeader("COLOR DE OJOS");


        TextField pideId = new TextField("Id de consulta");
        pideId.addClassName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button boton1 = new Button("Say hello", e -> {
            String tipo = comboBox.getValue();
            int id = Integer.parseInt(pideId.getValue());
            try {
                resultados.removeAll();
                resultados.add(service.getSWAPI(tipo, id));
            } catch (Exception ex) {
            }
        });

        Button boton2 = new Button("Lee todos los personajes", e -> {
            String tipo = comboBox.getValue();
            try {
                grid.setItems(service.getCaracterSWList(tipo));
                resultadosGrid.add(grid);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
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
        entradas.add(comboBox, pideId,boton1, boton2);
        add(entradas,  resultados, resultadosGrid);
    }

}
