package parkhomov.andrew.lensthicknesscalculator.activities.fragment.glossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parkhomov.andrew.lensthicknesscalculator.R;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.CONSTANT;
import parkhomov.andrew.lensthicknesscalculator.activities.fragment.Parent;
import parkhomov.andrew.lensthicknesscalculator.activities.utils.Utils;

/**
 * Class glossary list display lists with parameters titles, witch present in program.
 */

public class GlossaryList extends Parent {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.header)
    TextView header;

    private List<String> headers;
    private List<String> description;
    private List<Integer> images;

    public static GlossaryList getInstance(List<String> headers, List<String> description, List<Integer> images) {
        Bundle bundle = new Bundle();
        GlossaryList glossaryList = new GlossaryList();
        glossaryList.setArguments(bundle);
        glossaryList.setHeaders(headers);
        glossaryList.setDescription(description);
        glossaryList.setImages(images);
        return glossaryList;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.glossary_list, container, false);
        ButterKnife.bind(this, view);

        header.setText(Utils.spacing(getString(R.string.header_glossary_list), CONSTANT.FRAGMENT_HEADER_SPACING_DISTANCE_0_4));
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
        Simple simple = new Simple(headers, description, images);

        recyclerView.setAdapter(simple);
    }

    @OnClick(R.id.turnBackImgB)
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    public class Simple extends RecyclerView.Adapter<GlossaryList.Simple.ListViewHolder> {

        private List<String> headers;
        private List<String> description;
        private List<Integer> images;

        private Simple(List<String> headers, List<String> description, List<Integer> images) {
            this.headers = headers;
            this.description = description;
            this.images = images;
        }

        // create headers in list
        @Override
        public GlossaryList.Simple.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.glossary_list_item, parent, false);
            return new GlossaryList.Simple.ListViewHolder(view);
        }

        // set images and names to each list item
        @Override
        public void onBindViewHolder(final GlossaryList.Simple.ListViewHolder holder, final int position) {
            final String docName = headers.get(position);
            holder.itemName.setText(docName);
            holder.itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        getFragmentManager()
                                .beginTransaction()
                                .addToBackStack(null)
                                .add(R.id.mainContainerConstr,
                                        GlossaryDetails.getInstance(
                                                headers.get(position),
                                                description.get(position),
                                                images.get(position)),
                                        CONSTANT.GLOSSARY_DETAILS)
                                .commit();
                    } catch (IllegalStateException e) {
                        Log.d(CONSTANT.MY_EXCEPTION, e.toString() + "");
                    }
                }
            });
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
