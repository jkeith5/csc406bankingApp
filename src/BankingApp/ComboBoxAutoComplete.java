package BankingApp;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

public class ComboBoxAutoComplete<T> extends ComboBox {

    // class that controls the comboBox and allows me to filter the items with each action in the controller
    private ComboBox<T> cmb;
    public String filter = "";
    private ArrayList<T> originalItems;
    private ArrayList<T> filteredList;


    public ComboBoxAutoComplete(ComboBox<T> cmb) {// constructor
        this.cmb = cmb;
        originalItems = getList(cmb.getItems());
        filteredList=new ArrayList<>();
        cmb.setTooltip(new Tooltip());
        cmb.setVisibleRowCount(10);
        this.cmb.setOnKeyPressed(this::keyEvent);
        this.cmb.setOnKeyReleased(this::keyEvent);
        this.cmb.setOnHiding(this::handleOnHiding);
        this.cmb.setOnShowing(this::handleOnShowing);
    }

    public void keyEvent(KeyEvent e){
        handleOnKeyPressed(e);
    }

    public void handleOnKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        cmb.getEditor().end();

        // these set the filter list for each keycode
        if(cmb.getEditor().getText().length() ==0){
            filter = "";
        }
        if(code.isLetterKey() && code != KeyCode.SPACE) {
            filter += e.getText();
        }
        if(code == KeyCode.SPACE){
            filter = cmb.getEditor().getText();
        }
        if(code == KeyCode.BACK_SPACE && filter.length() > 0) {
            cmb.getItems().setAll(originalItems);
            filter = cmb.getEditor().getText();
            cmb.getEditor().end();
        }
        if(code == KeyCode.ESCAPE) {
            filter = "";
        }
        if(code == KeyCode.DOWN || code == KeyCode.UP){
            if(cmb.getEditor().getText().length()==0){
                cmb.show();
            }else{
                return;
            }
        }
        if(code == KeyCode.ENTER){
            filter = cmb.getEditor().getText();
            return;
        }
        if(code == KeyCode.TAB ){
            filter = "";
            cmb.getEditor().setText(filter);
            cmb.getEditor().end();
        }
        if(!code.isLetterKey()){
            filter = filter.replaceAll("^\\d","");
            cmb.getEditor().setText(filter);
            cmb.getEditor().end();
        }
        if(filter.length() == 0) {
            filteredList = originalItems;
            cmb.getTooltip().hide();
        }else {
            String usrTxt = filter.toLowerCase();

            filteredList = filterList(originalItems,usrTxt);
            cmb.getTooltip().setText(usrTxt);
            Window stage = cmb.getScene().getWindow();
            double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
            double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
            cmb.getTooltip().show(stage, posX, posY);
            cmb.show();
        }

        if(filteredList.size()==0){
            filter = "";
            filteredList = originalItems;
            cmb.getEditor().setText(filter);
            cmb.getEditor().end();
            cmb.getItems().setAll(filteredList);
        }else{
            cmb.getItems().setAll(filteredList);
        }

    }

    public void handleOnShowing(Event e){
        cmb.getSelectionModel().clearSelection();
    }

    public void handleOnHiding(Event e) {
        setSelectedItem(cmb);
        filter = "";
        cmb.getTooltip().hide();
        cmb.getItems().setAll(originalItems);
        filteredList=originalItems;
    }

    public void setSelectedItem(ComboBox<T> cmb){
        if(cmb.getSelectionModel().isEmpty()){
            cmb.getSelectionModel().clearAndSelect(0);
        }else{
            cmb.getSelectionModel().clearAndSelect(cmb.getSelectionModel().getSelectedIndex());
        }
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
