package com.vaadin.testapp.ui.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.testapp.ui.main.MainView;

@Route(value = UserForm.ROUTE, layout = MainView.class)
public class UserForm extends VerticalLayout {

    public static final String ROUTE = "user";
    public static final String TITLE = "Exercise";

    public UserForm() {

        add(new TextField("userName"));
        add(new TextArea("Description"));

        add(new TextField("password"));
        add(new TextArea("Description"));

        add(new TextField("firstName"));
        add(new TextArea("Description"));

        add(new TextField("lastName"));
        add(new TextArea("Description"));

        NumberField price = new NumberField("Price");
        price.setSuffixComponent(new Span("€"));
        price.setStep(0.01);
        add(price);

        add(new DatePicker("Available"));

        ComboBox<String> category = new ComboBox<>("Category");
        category.setItems("A", "B", "C");
        add(category);

        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Cancel");
        VerticalLayout buttons = new VerticalLayout(save, cancel);
        add(buttons);
    }
}
