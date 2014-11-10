package com.example.joe.networkbattleship;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Joe on 10/30/2014.
 */
public class GameListFragment extends Fragment implements ListAdapter{

    public interface OnGameSelectedListener {
        public void onGameSelected(String gameName);
    }

    private OnGameSelectedListener onGameSelectedListener;
    private static TextView selectedView;
    private int selectedPosition;

    public void setOnGameSelectedListener(OnGameSelectedListener listener) {
        onGameSelectedListener = listener;
    }

    public OnGameSelectedListener getOnGameSelectedListener() {
        return onGameSelectedListener;
    }

    public void setSelectedView(String gameName) {

    }

    private UUID[] gameIdentifiers = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView gameView = new ListView(getActivity());
        gameView.setAdapter(this);

        ArrayList<UUID> identifiers = BattleshipController.getInstance().getGameIdentifiers();
        gameIdentifiers = identifiers.toArray(new UUID[identifiers.size()]);


        BattleshipController.getInstance().setOnListChangedListener(new BattleshipController.OnListChangedListener() {
            @Override
            public void onListChange() {

                ArrayList<UUID> identifiers = BattleshipController.getInstance().getGameIdentifiers();
                gameIdentifiers = identifiers.toArray(new UUID[identifiers.size()]);
                gameView.invalidateViews();
            }
        });

        return gameView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() > 0;
    }

    @Override
    public int getCount() {
        return BattleshipController.getInstance().getGameIdentifiers().size();
    }

    @Override
    public Object getItem(int position) {
        if(gameIdentifiers == null) {
            ArrayList<UUID> identifiers = BattleshipController.getInstance().getGameIdentifiers();
            gameIdentifiers = identifiers.toArray(new UUID[identifiers.size()]);
        }
        return BattleshipController.getInstance().getGameState(gameIdentifiers[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(gameIdentifiers == null) {
            ArrayList<UUID> identifiers = BattleshipController.getInstance().getGameIdentifiers();
            gameIdentifiers = identifiers.toArray(new UUID[identifiers.size()]);
        }
        UUID identifier = gameIdentifiers[position];
        GameState state = BattleshipController.getInstance().getGameState(identifier);

        TextView gameView = new TextView(getActivity());
        gameView.setText(state.toString());

        gameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onGameSelectedListener != null) {

                    TextView view = (TextView)v;
                    String name = view.getText().toString().split("\n")[0];
                    onGameSelectedListener.onGameSelected(name);
                    if(selectedView != null)
                        selectedView.setBackgroundColor(Color.WHITE);
                    view.setBackgroundColor(Color.YELLOW);
                    selectedView = view;
                }
            }
        });
        return gameView;
    }

    public static void updateSelectedView(GameState state) {

        //THIS SHOULD BE UNECESSARY
        if(selectedView == null) {
            return;
        }

        selectedView.setText(state.toString());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
}