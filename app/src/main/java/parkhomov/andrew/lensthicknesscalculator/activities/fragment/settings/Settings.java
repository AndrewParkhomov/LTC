package parkhomov.andrew.lensthicknesscalculator.activities.fragment.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.Parent;


public class Settings extends Parent {

    @BindView(R.id.settingsRcycV)
    RecyclerView recyclerView;

    public static Settings getInstance() {
        Bundle bundle = new Bundle();
        Settings settings = new Settings();
        settings.setArguments(bundle);
        return settings;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, view);

        setUpDateInAdapter();
        return view;
    }

    private void setUpDateInAdapter() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        // make divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                lm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        List<String> headers = new ArrayList<>(3);
        headers.add(0, getString(R.string.header_language));
        headers.add(1, getString(R.string.header_rate_this_app));
        headers.add(2, getString(R.string.header_about));
        Simple simple = new Simple(headers);

        recyclerView.setAdapter(simple);
    }

    @OnClick(R.id.turnBackImgB)
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }


    public class Simple extends RecyclerView.Adapter<Settings.Simple.ListViewHolder> {

        private List<String> headers;


        private Simple(List<String> headers) {
            this.headers = headers;
        }

        // create headers in list
        @Override
        public Settings.Simple.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.glossary_list_item, parent, false);
            return new Settings.Simple.ListViewHolder(view);
        }

        // set images and names to each list item
        @Override
        public void onBindViewHolder(final Settings.Simple.ListViewHolder holder, final int position) {
            final String docName = headers.get(position);
            holder.itemName.setText(docName);
            holder.itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            Language language = Language.getInstance();
                            language.show(getFragmentManager(), CONSTANT.LANGUAGE);
                            break;
                        case 1:
                            rateThisAppClicked();
                            break;
                        case 2:
                            AboutDialogActivity aboutDialogActivity = AboutDialogActivity.getInstance();
                            aboutDialogActivity.show(getFragmentManager(), CONSTANT.ABOUT);
                            break;
                    }
                }
            });
        }

        private void rateThisAppClicked() {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.rate_app_header)
                    .setMessage(R.string.rate_app_body)
                    .setNegativeButton(R.string.rate_app_dialog_no, null)
                    .setPositiveButton(R.string.rate_app_dialog_ok, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CONSTANT.GOOGLE_PLAY_LINK)));
                        }
                    }).create().show();
        }

        // size of list
        @Override
        public int getItemCount() {
            return headers != null ? headers.size() : 0;
        }

        // holder for list headers
        class ListViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.itemNameTxtV)
            TextView itemName;

            private ListViewHolder(final View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
