package com.scorp.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scorp.loftcoin.databinding.LiWalletBinding;

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.ViewHolder> {

    private LayoutInflater inflater;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LiWalletBinding binding = LiWalletBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull LiWalletBinding binding) {
            super(binding.getRoot());
        }
    }
}
