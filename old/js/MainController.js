var MainController = {

    view: {
        navigateToExSum: function(){},
        navigateToExGetRemote: function(){}
    },
    onClickExSum: function(){
        console.log('onClickExSum');
        this.view.navigateToExSum();
    },
    onClickExGetRemote: function(){
         console.log('onClickExGetRemote');
        this.view.navigateToExGetRemote();
    }
};