package me.boger.pjsua2.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.boger.pjsua2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SipCallFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_call_display)
    TextView tvDisplay;
    @Bind(R.id.tv_call_answer)
    TextView tvAnswer;
    @Bind(R.id.tv_call_reject)
    TextView tvReject;

    public SipCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sip_call, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        tvAnswer.setOnClickListener(this);
        tvReject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
