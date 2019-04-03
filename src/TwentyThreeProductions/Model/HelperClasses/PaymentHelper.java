package TwentyThreeProductions.Model.HelperClasses;

public class PaymentHelper {

    private static PaymentHelper paymentHelper;
    private float amountPaid;


    private PaymentHelper() {}

    public static PaymentHelper getInstance() {
        if (paymentHelper == null) {
            paymentHelper = new PaymentHelper();
        }
        return paymentHelper;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public float getAmountPaid() {
        return amountPaid;
    }
}
