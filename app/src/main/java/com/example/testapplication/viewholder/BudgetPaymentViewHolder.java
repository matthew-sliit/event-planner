package com.example.testapplication.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.db.budget.Budget_payments;

public class BudgetPaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView bp_name, bp_date, bp_amt; //from layout
    public ImageButton btn_paid, btn_delete;
    /*
     * ================== ViewHolder constructor ========================
     * @param itemView
     *         The layout view group used to display the data
     * @param currentAct
     *         The context of the activity
     */
    public BudgetPaymentViewHolder(final View itemView, Context currentAct) {
        super(itemView);
        bp_name = (TextView) itemView.findViewById(R.id.sc_pay_name);
        bp_date = (TextView) itemView.findViewById(R.id.sc_pay_belowName);
        bp_amt = (TextView) itemView.findViewById(R.id.sc_pay_rightTop);
        btn_paid = (ImageButton)itemView.findViewById(R.id.sc_image_btn_paid);
        btn_delete = (ImageButton)itemView.findViewById(R.id.sc_image_btn_del);
        itemView.setOnClickListener(this);
    }
    public Context currentAct;//passed from ActivityClass through Adapter
    public Budget_payments pay_model; //instance var
    /*
     * ================== bindData ========================
     * method to bind data to adapter
     *
     * @param budget_model
     *         The viewmodel that contains the data
     */
    public void bindData(Budget_payments payment) {
        if(payment != null){
            //Log.d("BudgetViewModel>>","id -> " + budget_model.id);
            this.pay_model = payment;
            bp_name.setText(payment.name);
            bp_date.setText(payment.rdate.toString());
            bp_amt.setText("0.00");
        }else{
            //this only happens if null list is passed
            this.pay_model = new Budget_payments(currentAct);
            bp_name.setText("");
            bp_date.setText("");
            bp_amt.setText("0.00");
        }
    }
    /*
     * ================== itemView:onClick ========================
     * handles onclick of any item in recycle view
     */
    @Override
    public void onClick(View view) {

        Log.d("BudgetPayment>>","AddPayment Activity Required to continue!");

        /*
        Intent i = new Intent(view.getContext(), EditPaymentActivity.class);
        Bundle b = new Bundle();
        //pass eventid, budgetid and payid
        b.putInt("eid",pay_model.event_id);
        b.putInt("bid",pay_model.budget_id);
        b.putInt("pid",pay_model.payment_id);
        i.putExtras(b);
        view.getContext().startActivity(i);
         */
    }
}
