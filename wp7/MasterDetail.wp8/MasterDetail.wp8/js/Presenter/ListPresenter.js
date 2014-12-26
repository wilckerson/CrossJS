var ListView = {
    populateList:function(items){},
    navigateToDetail: function(){}
};

var ListPresenter = {
    
    native_onLoad : function(){
        var jsonResult = JSON.stringify(ItemsModel.items);
        ListView.populateList(jsonResult);
    },
    
    native_onItemClick: function(idx){
        ItemDetailPresenter.item = ItemsModel.items[idx];
        ListView.navigateToDetail();
    }
};