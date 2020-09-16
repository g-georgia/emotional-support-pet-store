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

    const checkBox = document.getElementById("same-address");
    checkBox.addEventListener("click", shippingAddressIsBillingAddress);

}

function shippingAddressIsBillingAddress() {
    const billingAddress = document.getElementById("billing-address");

    if (!this.checked){
        billingAddress.style.display = "block";
    } else {
        billingAddress.style.display = "none";
    }
}

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
                            <button class="btn btn-success">Add to cart</button>
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
            button.addEventListener("click", getSelectedProduct);
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
        totalPrice += Math.round(order.subtotalPrice*100.0)/100.0;
        let orderDetails = `
                            <div class="${order.name}">
                                <p>Name: ${order.name}</p>
                                <p>Quantity: ${order.quantity} 
                                    <button type="button" class="btn btn-success btn-sm plus" id="plus" data-orderId="${order.id}">+</button>
                                    <button type="button" class="btn btn-success btn-sm minus" id="minus" data-orderId="${order.id}">-</button>
                                </p>
                                <p>Price per unit: ${order.defaultPrice}</p>
                                <p>Subtotal price: ${Math.round(order.subtotalPrice*100.0)/100.0} ${order.defaultCurrency}</p>  
                                <hr>                        
                            </div>
                            `;
        modalContent += orderDetails;
        currency = order.defaultCurrency;

    }

    modalContent += `
                    <div>
                        <strong><p>Total: ${totalPrice.toFixed(2)} ${currency}</p></strong>
                    </div>
                    `;


    let modal = document.querySelector(".modal-body");
    modal.innerHTML = modalContent;
    document.querySelector(".cart-item-number").innerText = numOfCartItems;


    let plusButtons = document.querySelectorAll(".plus");
    for (let button of plusButtons) {
        if (!(button.classList.contains("hasEventListener"))) {
            button.classList.add("hasEventListener");
            button.addEventListener("click", (event)=> {
                let orderId = event.target.getAttribute("data-orderId");
                increaseProductNumber(orderId);
            });
        }

    }

    let minusButtons = document.querySelectorAll(".minus");
    for (let button of minusButtons) {
        if (!(button.classList.contains("hasEventListener"))) {
            button.classList.add("hasEventListener");
            button.addEventListener("click", (event)=> {
                let orderId = event.target.getAttribute("data-orderId");
                decreaseProductNumber(orderId);
            });
        }
    }

}

function increaseProductNumber(orderId) {
    fetch(`/order/product-to-increase?${orderId}`)
        .then(response => response.json())
        .then(newOrderList => changeCartContent(newOrderList))
}

function decreaseProductNumber(orderId) {
    fetch(`/order/product-to-decrease?${orderId}`)
        .then(response => response.json())
        .then(newOrderList => changeCartContent(newOrderList))
}

addEventListeners();