package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DashboardFragment extends Fragment {
    String userID;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Posts> options;
    FirebaseRecyclerAdapter<Posts, MyViewHolderThree> adapter3;
    DatabaseReference dataRef;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dataRef = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView = v.findViewById(R.id.db_result_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final TextView nameTextView = (TextView) v.findViewById(R.id.db_fullname);


        //sets the user's name in the Dashboard page
        dataRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                nameTextView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        //Loads data from the firebase
        LoadData();

        return v;
    }


    //Loads data from the firebase
    private void LoadData() {

        dataRef = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("userPosts");
        //sort post by most recent posting
        Query query = dataRef.orderByChild("timeMili");
        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts.class).build();
        adapter3 = new FirebaseRecyclerAdapter<Posts, MyViewHolderThree>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolderThree holder, int position, @NonNull Posts model) {
                //get the unique Postkey in the database to reference it and get the post information details
                String PostID = getRef(position).getKey();
                //set the cardholder display values
                holder.postTitle.setText(model.getTitle());
                holder.price.setText(model.getPrice());
                holder.time.setText(model.getDate());    //set time
                Picasso.get().load(model.getImage()).into(holder.imageView);

                //when user clicks on the post, it brings them to the page that they can view the full post information
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityPostViewPersonal.class);
                        intent.putExtra("PostID", PostID);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolderThree onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_double_post_display, parent,false);  //in their case is single_view, check this if not working
                return new MyViewHolderThree(v);
            }
        };

        adapter3.startListening();
        recyclerView.setAdapter(adapter3);
    }

}

