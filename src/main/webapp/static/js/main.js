function addEventListeners() {
    const productCategories = document.querySelectorAll(".productCategory");
    for (let productCategory of productCategories) {
        productCategory.addEventListener("click", getProductsToProductCategory);
    }

    const suppliers = document.querySelectorAll(".supplier");
    for (let supplier of suppliers) {
        supplier.addEventListener("click", getProductsToSupplier);
    }

    const addToCartButtons = document.querySelectorAll(".btn.btn-success");
    for (let button of addToCartButtons) {
        button.addEventListener("click", getSelectedProduct);

    }


    /* const productCategoryFilter = document.querySelector(".filter-product-category")
     productCategoryFilter.addEventListener("click", showProductCategories);
     productCategoryFilter.addEventListener("mouseover", changeBackgroundColorBlack);
     productCategoryFilter.addEventListener("mouseout", changeBackgroundColorWhite);

     const supplierFilter = document.querySelector(".filter-supplier")
     supplierFilter.addEventListener("click", showSuppliers);
     supplierFilter.addEventListener("mouseover", changeBackgroundColorBlack);
     supplierFilter.addEventListener("mouseout", changeBackgroundColorWhite);*/

}


/*function changeBackgroundColorBlack() {
    this.style.backgroundColor = "black";
    this.style.color = "white";

}

function changeBackgroundColorWhite() {
    this.style.backgroundColor = "white";
    this.style.color = "black";
}*/

/*function showSuppliers(event) {
    let options = document.querySelectorAll(".hidden-supplier");
    if (options.length !== 0) {
        for (let option of options) {
            option.classList.remove("hidden-supplier");
            event.target.removeEventListener("mouseout", changeBackgroundColorWhite)
        }
    } else {
        let optionsToHide = document.querySelectorAll(".supplier-options");
        for (let option of optionsToHide) {
            option.classList.add("hidden-supplier");
            event.target.addEventListener("mouseout", changeBackgroundColorWhite)
        }
    }

}*/

/*function showProductCategories(event) {
        let options = document.querySelectorAll(".hidden-category");
    if (options.length !== 0) {
        for (let option of options) {
            option.classList.remove("hidden-category");
            event.target.removeEventListener("mouseout", changeBackgroundColorWhite)
        }
    } else {
        let optionsToHide = document.querySelectorAll(".product-category");
        for (let option of optionsToHide) {
            option.classList.add("hidden-category");
            event.target.addEventListener("mouseout", changeBackgroundColorWhite)
        }
    }


}*/

function getProductsToSupplier() {
    let supplierId = this.id;
    fetch(`/supplier?${supplierId}`)
        .then(response => response.json())
        .then(productsToList => buildDom(productsToList))
}


function getProductsToProductCategory() {
    let productCategoryId = this.id;
    fetch(`/product-category?${productCategoryId}`)
        .then(response => response.json())
        .then(productsToList => buildDom(productsToList))
}

function buildDom(productsToList) {
    let products = "";
    for (let product of productsToList) {
        let productDetails = `
            <div class="col col-sm-12 col-md-6 col-lg-4">
                <div class="card">
                    <img class="image" src="/static/img/product_${product.id}.jpg" alt="${product.name}" />
                    <div class="card-header">
                        <h4 class="card-title" id="${product.id}">${product.name}</h4>
                        <p class="card-text">${product.description}</p>
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <p class="lead">${product.defaultPrice} ${product.defaultCurrency}</p>
                        </div>
                        <div class="card-text">
                            <a class="btn btn-success" href="#">Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>`;

        products += productDetails;
    }
    document.querySelector("#products").innerHTML = products;
    const addToCartButtons = document.querySelectorAll(".btn.btn-success");
    for (let button of addToCartButtons) {
        if (!(button.classList.contains("hasEventListener"))) {
            button.addEventListener("click", getSelectedProduct)
        }

    }
}

function getSelectedProduct(e) {
    this.classList.add("hasEventListener");
    let selectedElementId = this.parentElement.parentElement.parentElement.children[1].children[0].id;
    fetch(`/order/product?${selectedElementId}`)
        .then(response => response.json())
        .then(orderList => changeCartContent(orderList))
}

function changeCartContent(orderList) {
    console.log(orderList);
    let numOfCartItems = 0;
    let modalContent = "";
    let totalPrice = 0;
    let currency = "";
    for (let order of orderList) {
        numOfCartItems += order.quantity;
        totalPrice += order.subtotalPrice;
        let orderDetails = `
                            <div class="${order.name}">
                                <p>Name: ${order.name}</p>
                                <p>Quantity: ${order.quantity} <button type="button" class="btn btn-success btn-sm plus" id="plus" data-buttonId="${order.id}">+</button> <button type="button" class="btn btn-success btn-sm minus" id="minus">-</button></p>
                                <p>Price per unit: ${order.defaultPrice}</p>
                                <p>Subtotal price: ${order.subtotalPrice} ${order.defaultCurrency}</p>  
                                <hr>                        
                            </div>
                            `;
        modalContent += orderDetails;
        currency = order.defaultCurrency;

    }

    modalContent += `
                    <div>
                        <strong><p>Total: ${totalPrice} ${currency}</p></strong>
                    </div>
                    `;


    let modal = document.querySelector(".modal-body");
    modal.innerHTML = modalContent;
    document.querySelector(".cart-item-number").innerText = numOfCartItems;
    let plusButtons = document.querySelectorAll(".plus");
    for (let button of plusButtons) {
        button.addEventListener("click", (event)=> {
            console.log(this.name);
            increaseProductNumber(orderList);
        });
    }

    let minusButtons = document.querySelectorAll(".minus");
    for (let button of minusButtons) {
        button.addEventListener("click", decreaseProductNumber);
    }

}

function increaseProductNumber(orderList) {
    alert("plus");
    let newQuantity = 0;
    let newSubtotalPrice = 0;
    let newTotalPrice = 0;
    // for (let item of orderList) {
    //
    // }

}

function decreaseProductNumber() {
    alert("minus");
}

addEventListeners();