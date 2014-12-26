var ItemDetailView = {
    setTitle: function(value){},
    setLabel: function(value) {},
    goBack: function() {}
};


var ItemDetailPresenter = {
    item: undefined,
    
    native_onLoad:function(){
        
        if(this.item)
        {
            ItemDetailView.setTitle(this.item);
            ItemDetailView.setLabel(this.item);
        }
    },
    
    native_onClickBack: function(){
	
        ItemDetailView.goBack();
    }
};