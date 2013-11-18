/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myatm;

public class ATM {

    public static class NoCardInserted extends Exception {

        public NoCardInserted(String string) {
        }
    }

    public static class NotEnoughMoneyInATM extends Exception {

        public NotEnoughMoneyInATM(String string) {
        }
    }

    public static class NotEnoughMoneyInAccount extends Exception {

        public NotEnoughMoneyInAccount(String string) {
        }
    }
    
    private Account acc;
    private double moneyATM;
    private boolean cardBlockATM;
        
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM){
        moneyATM = moneyInATM;
    }

    public double getMoneyInATM() {
        return moneyATM;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode) throws NoCardInserted{
            acc = card.getAccount();
            if(cardBlockATM) 
                throw new NoCardInserted("oops");
            
            else if(!card.isBlocked() && card.checkPin(pinCode))
                return true;
            else
                cardBlockATM = true;
                return false;
    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance(){
        return acc.getBalance();
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount) throws NotEnoughMoneyInATM, NotEnoughMoneyInAccount{
            if(acc.getBalance() <= amount)
                throw new NotEnoughMoneyInAccount("oops");
            
            else if(moneyATM <= amount)
                throw new NotEnoughMoneyInATM("oops");
            else
                moneyATM -= acc.withdrow(amount);
            
        return acc.getBalance();
    }
}
