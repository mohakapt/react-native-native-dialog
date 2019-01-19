package com.github.mohaka;

import android.app.Activity;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.ArrayList;
import java.util.List;

public class ItemsDialogOptions extends DialogOptions {
    public final static int MODE_DEFAULT = 100;
    public final static int MODE_SINGLE = 101;
    public final static int MODE_MULTIPLE = 102;

    private int mode;
    private List<Item> items;
    private List<Object> selectedIds = new ArrayList<>();

    public ItemsDialogOptions() {
    }

    public ItemsDialogOptions(ReadableMap map) {
        this.populate(map);
    }

    public int getMode() {
        return mode;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Object> getSelectedIds() {
        return selectedIds;
    }

    public String[] getTitles() {
        if (Utilities.isEmpty(items))
            return new String[]{};

        String[] reVal = new String[items.size()];
        for (int i = 0; i < items.size(); i++)
            reVal[i] = items.get(i).title;

        return reVal;
    }

    public Object[] getSelections() {
        switch (getMode()) {
            case MODE_DEFAULT:
            default:
                return new Object[]{};
            case MODE_SINGLE:
                if (!Utilities.isEmpty(items) && !Utilities.isEmpty(selectedIds))
                    for (int i = 0; i < items.size(); i++)
                        if (items.get(i).getId().equals(selectedIds.get(0)))
                            return new Object[]{i};
                return new Object[]{-1};
            case MODE_MULTIPLE:
                Object[] checkedItems = new Boolean[items.size()];
                for (int i = 0; i < checkedItems.length; i++)
                    if (selectedIds.contains(items.get(i).getId()))
                        checkedItems[i] = true;
                return checkedItems;
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setMode(String mode) {
        switch (mode.toLowerCase()) {
            case "default":
            default:
                setMode(MODE_DEFAULT);
                break;
            case "single":
                setMode(MODE_SINGLE);
                break;
            case "multiple":
                setMode(MODE_MULTIPLE);
                break;
        }
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setSelectedIds(List<Object> selectedIds) {
        this.selectedIds = selectedIds;
    }

    @Override
    public void populate(ReadableMap map) {
        super.populate(map);

        if (map.hasKey("mode")) setMode(map.getString("mode"));

        if (map.hasKey("items")) {
            List<Item> itemList = new ArrayList<>();

            ReadableArray itemsArray = map.getArray("items");
            for (int i = 0; i < itemsArray.size(); i++)
                itemList.add(new Item.Builder().populate(itemsArray.getMap(i)).build());

            setItems(itemList);
        }

        if (map.hasKey("selectedItems")) {
            List<Object> selectedIdList = new ArrayList<>();

            switch (map.getType("selectedItems")) {
                case Number:
                    selectedIdList.add(map.getInt("selectedItems"));
                    break;
                case String:
                    selectedIdList.add(map.getString("selectedItems"));
                    break;
                case Array:
                    ReadableArray selectedArray = map.getArray("selectedItems");
                    for (int i = 0; i < selectedArray.size(); i++) {
                        switch (selectedArray.getType(i)) {
                            case Number:
                                selectedIdList.add(selectedArray.getInt(i));
                                break;
                            case String:
                                selectedIdList.add(selectedArray.getString(i));
                                break;
                        }
                    }
                    break;
            }

            setSelectedIds(selectedIdList);
        }
    }

    public AlertDialog showDialog(Activity activity, @StyleRes int dialogTheme) {
        AlertDialog.Builder builder = super.buildDialog(activity, dialogTheme);

        String[] titles = getTitles();
        Object[] selections = getSelections();
        switch (mode) {
            case MODE_DEFAULT:
            default:
                builder.setItems(titles, getClickListener());
                break;
            case MODE_SINGLE:
                builder.setSingleChoiceItems(titles, (Integer) selections[0], null);
                break;
            case MODE_MULTIPLE:
                builder.setMultiChoiceItems(titles, Utilities.toBooleanArray(selections, false), null);
                break;
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(isCancelOnTouchOutside());
        alertDialog.show();
        return alertDialog;
    }

    public static class Item {
        private Object id;
        private boolean idNumber;
        private String title;

        private Item() {
        }

        public Object getId() {
            return id;
        }

        public boolean isIdNumber() {
            return idNumber;
        }

        public String getTitle() {
            return title;
        }

        public static class Builder {
            private Item mItem;

            public Builder() {
                mItem = new Item();
            }

            public Builder setId(int id) {
                this.mItem.id = id;
                this.mItem.idNumber = true;
                return this;
            }

            public Builder setId(String id) {
                this.mItem.id = id;
                this.mItem.idNumber = false;
                return this;
            }

            public Builder setTitle(String title) {
                this.mItem.title = title;
                return this;
            }

            public Builder populate(ReadableMap map) {
                if (map.hasKey("id")) {
                    switch (map.getType("id")) {
                        case Number:
                            setId(map.getInt("id"));
                            break;
                        case String:
                            setId(map.getString("id"));
                            break;
                    }
                }
                if (map.hasKey("title")) setTitle(map.getString("title"));
                return this;
            }

            public Item build() {
                return mItem;
            }
        }
    }
}
