var ListView = {
    populateList:function(items){},
    navigateToDetail: function(){}
};

var ListPresenter = {
    
    native_onLoad : function(){
     
        ListView.populateList(ItemsModel.items);
    },
    
    native_onItemClick: function(idx){
        ItemDetailPresenter.item = ItemsModel.items[idx];
        ListView.navigateToDetail();
    }
};