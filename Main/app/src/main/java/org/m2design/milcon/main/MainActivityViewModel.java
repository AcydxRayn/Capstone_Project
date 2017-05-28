package org.m2design.milcon.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.m2design.milcon.models.User;

/**
 * Created by ajmyr on 5/27/2017.
 */

public class MainActivityViewModel {

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    private MutableLiveData<DataSnapshot> mSnapshot;
    private MutableLiveData<User> mMentor;
    private MutableLiveData<FirebaseUser> currentUser;

    public MutableLiveData<FirebaseUser> getCurrentUser() {
        if (currentUser == null) {
            currentUser = new MutableLiveData<>();
            loadCurrentUser();
        }
        return currentUser;
    }

    private void loadCurrentUser() {
        Handler handler = new Handler();

        handler.postDelayed(() -> currentUser.setValue(FirebaseAuth.getInstance().getCurrentUser()),
                2000);
    }

    public void getMentorList(@NonNull DatabaseReference ref, @Nullable Integer quantityToLoad) {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mRef = ref;
            loadMentorList();
        }
    }

    private void loadMentorList() {
        Handler handler = new Handler();

        handler.post(() -> {
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void lifeEventResume() {


    }


    class MainActivityRecyclerAdapter extends RecyclerView
            .Adapter<MainActivityRecyclerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(
                ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(
                ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
