package io.devsummit.android.Controllers;

import io.devsummit.android.Activities.OrderedTicketActivity;
import io.devsummit.android.Models.OrderedTicketModel;
import io.devsummit.android.Remote.APIService;
import io.devsummit.android.Remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wlisrausr on 10/2/17.
 */

public class OrderedTicketController {
    public OrderedTicketModel OrderedTickets;
    private APIService mAPIService;
    private OrderedTicketActivity mOrderedTicket;

    public OrderedTicketController(OrderedTicketActivity act) {
        OrderedTickets = new OrderedTicketModel();
        mAPIService = ApiUtils.getAPIService();
        mOrderedTicket = act;
    }

    public void getOrderedTickets(String token){
        mAPIService.fetchOrderedTickets(token).enqueue(new Callback<OrderedTicketModel>() {
            @Override
            public void onResponse(Call<OrderedTicketModel> call, Response<OrderedTicketModel> response) {
                OrderedTickets = response.body();
                mOrderedTicket.setAdapter(OrderedTickets);
            }

            @Override
            public void onFailure(Call<OrderedTicketModel> call, Throwable t) {
                OrderedTickets = new OrderedTicketModel();
            }
        });

        //return OrderedTickets;
    }
}
