var ListView ={
    populateList:function(items){},
    nativageToDetail: function(){}
};

var ListPresenter = {
    
    onLoad : function(){
     
        ListView.populateList(ItemsModel.items);
    },
    
    onItemClick: function(item){
        
        ItemDetailPresenter.item = item;
        ListView.nativageToDetail();
    }
};