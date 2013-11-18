/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myatm;

public interface Card {
    //Заблокирована ли карта или нет
    public boolean isBlocked();
    
    //Возвращает счет связанный с данной картой
    public Account getAccount();

    //Проверяет корректность пин-кода
    public boolean checkPin(int pinCode);    
}
