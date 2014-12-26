package com.crossjs;

class CSString{
	
	public static boolean IsNullOrEmpty(String item)
	{
		return item != null || (item.length() == 0);
	}
}

interface ItemDetailView
{
    void setTitle(CSString value);
    void setLabel(CSString value);
    void goBack();
}

class ItemDetailPresenter
{
    ItemDetailView view;
    public static String item;

	public ItemDetailPresenter(ItemDetailView view)
    {
        this.view = view;
    }

    public void onLoad()
    {
        if(CSString.IsNullOrEmpty(item))
        {
            view.setTitle(item);
            view.setLabel(item);
        }
    }

    public void onClickBack()
    {
        view.goBack();
    }
}

interface ListView
{
    void populateList(string[] items);
    void navigateToDetail();
}

public static class ItemsModel
{
    public static string[] items = { "Vinicius", "Wilckerson", "Del" };
}

public class ListPresenter
{
    ListView view;
    public ListPresenter(ListView view)
    {
        this.view = view;
    }

    void onLoad()
    {
        view.populateList(ItemsModel.items);
    }

    void onItemClick(int idx)
    {
        ItemDetailPresenter.item = ItemsModel.items[idx];
        ListView.navigateToDetail();
    }
}

