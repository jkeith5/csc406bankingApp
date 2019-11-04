package BankingApp;

import java.sql.Wrapper;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

public class ComboBoxAutoComplete<T> {

    // class that controls the comboBox and allows me to filter the items with each action in the controller
    private ComboBox<T> cmb;
    public String filter = "";
    private ArrayList<T> originalItems;

    public ComboBoxAutoComplete(ComboBox<T> cmb) {// constructor
        this.cmb = cmb;
        originalItems = getList(cmb.getItems());
        cmb.setTooltip(new Tooltip());
    }

    public void keyReleased(KeyEvent e){ //calls the keypress method
        handleOnKeyPressed(e);
    }

    public void onHiding(Event e){
        System.out.println("on hiding: "+e.getEventType().getName());
        handleOnHiding(e);
    }

    public void handleOnKeyPressed(KeyEvent e) {
        System.out.println(e.getEventType().getName()+": "+e.getCode().getName());
        ArrayList<T> filteredList = new ArrayList<>();

        KeyCode code = e.getCode();
        cmb.getEditor().end();

        // these set the filter list for each keycode
        if(cmb.getEditor().getText().length() ==0){
            filter = "";
            System.out.println("len 0 clear filter");
        }
        if(code.isLetterKey() && code != KeyCode.SPACE) {
            filter += e.getText();
            System.out.println("letter key add to filter");
        }
        if(code == KeyCode.SPACE){
            filter = cmb.getEditor().getText();
            System.out.println("code space get text to filter: "+filter);
        }
        if(code == KeyCode.BACK_SPACE && filter.length() > 0) {
            cmb.getItems().setAll(originalItems);
            filter = cmb.getEditor().getText();
            cmb.getEditor().end();
            System.out.println("backspace and filter > 0 set original set filter to text. filter: "+filter);
        }
        if(code == KeyCode.ESCAPE) {
            filter = "";
            System.out.println("escape reset filter");
        }
        if(code == KeyCode.DOWN || code == KeyCode.UP){
            System.out.println("up or down return");
            return;
        }
        if(code == KeyCode.ENTER){
            filter = cmb.getEditor().getText();
            System.out.println("Enter set filter to getText filter: "+filter);
            return;
        }
        if(code == KeyCode.TAB ){
            filter = cmb.getEditor().getText();
            System.out.println("Tab set filter to getText return filter: "+filter);
            return;
        }

        if(filter.length() == 0) {
            filteredList = originalItems;
            cmb.getTooltip().hide();
            System.out.println("filter len ==0 set to original and hide filter: "+filter);
        }else {
            ArrayList<T> itemsT = getList(cmb.getItems());
            System.out.println("itemsT tostring: "+itemsT.toString());

            ArrayList<T> items = getList(cmb.getItems());
            String usrTxt = filter.toLowerCase();

            filteredList = filterList(items,usrTxt);

            if(filteredList.size()==0){
                System.out.println("no items match filter: "+filter);
            }

            System.out.println("applied filter");
            System.out.println("Filter: "+filter+" usrTxt: "+usrTxt);

            //test

            System.out.println("items Filtered: "+filteredList.toString());



            //test

            cmb.getTooltip().setText(usrTxt);
            Window stage = cmb.getScene().getWindow();
            double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
            double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
            cmb.getTooltip().show(stage, posX, posY);
            cmb.show();
        }
        cmb.getItems().setAll(filteredList);

    }

    public void handleOnHiding(Event e) {
        System.out.println("on Hiding 2: "+e.getEventType().getName());
        filter = "";
        cmb.getTooltip().hide();
        T s = cmb.getSelectionModel().getSelectedItem();
        cmb.getItems().setAll(originalItems);
        cmb.getSelectionModel().select(s);
    }

    public static <T> ArrayList<T> getList(ObservableList<T> elements){
        ArrayList<T> list = new ArrayList<>();

        for(T element: elements){
            list.add(element);
        }
        return list;

    }

    public ArrayList<T> filterList(ArrayList<T> items,String filter){
        ArrayList<T> returnVal = new ArrayList<>();

        for(T element:items){
            if(element.toString().toLowerCase().contains(filter)){
                returnVal.add(element);
            }
        }

        return returnVal;
    }


}
