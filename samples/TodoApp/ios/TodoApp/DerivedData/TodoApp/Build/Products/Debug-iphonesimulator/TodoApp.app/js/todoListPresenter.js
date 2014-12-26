var todoListNativeView = {
    //ListMethods
    setEmptyListMsgVisibility: function (isVisible) {},
    populateList: function (items) {},

    //LoadMethods
    loadTodoItemPresenter: function () {},

    //ItemMethods
    updateItemStatus: function (index, isDone) {}
};

var todoListPresenter = {

    items: [], //{description: "", done: false}

    fromNative_onLoad: function () {

        if (todoListPresenter.items.length == 0) {
            todoListNativeView.setEmptyListMsgVisibility(true);
        } else {
            todoListNativeView.populateList(JSON.stringify({data:todoListPresenter.items}));
            todoListNativeView.setEmptyListMsgVisibility(false);
        }
    },

    fromNative_onClickAdd: function () {
        todoItemPresenter.itemIndex = undefined;
        todoListNativeView.loadTodoItemPresenter();
    },

    fromNative_onChangeItemStatus: function (index) {

        var item = todoListPresenter.items[index];
        item.done = !item.done;
        todoListNativeView.updateItemStatus(index, item.done);
    },

    fromNative_onEditItem: function (index) {
        todoItemPresenter.itemIndex = index;
        todoListNativeView.loadTodoItemPresenter();
    },
};