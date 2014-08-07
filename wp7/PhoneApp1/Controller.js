var Controller = {
    view: {
        getNum1: function(){},
        getNum2: function(){},
        showMessage: function(msg){}
    },
    onClickSoma: function(){
        var n1 = this.view.getNum1();
        var n2 = this.view.getNum2();
         if(!n1 && n1 != 0 )
        {
            this.view.showMessage('Informe o primeiro numero.');
            return;
        }
        
        if(!n2 && n2 != 0)
        {
            this.view.showMessage('Informe o segundo numero.');
            return;
        }
        
        var result = n1 * n2;
        this.view.showMessage( n1 + ' x ' + n2 + ' = ' + result);
    }
};