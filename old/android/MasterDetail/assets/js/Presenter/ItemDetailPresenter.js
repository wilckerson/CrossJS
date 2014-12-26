var ItemDetailView = {
    setTitle: function(value){},
    setLabel: function(value) {},
    goBack: function() {}
};

var ItemDetailPresenter{
    item: undefined,
    
    onLoad:function(){
        
        if(this.item)
        {
            ItemDetailView.setTitle(this.item);
            ItemDetailView.setLabel(this.item);
        }
    }
    
    onClickBack: function(){
        ItemDetailView.goBack();
    }
};