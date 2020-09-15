init()

function init() {
    let greenAlert = document.getElementById("alert-box");
    greenAlert.classList.add('hidden');
    let confirmButton = document.getElementById("confirmation-button");
    confirmButton.addEventListener("click", confirmPayment)
}

function confirmPayment() {
    let greenAlert = document.getElementById("alert-box");
    greenAlert.classList.remove('hidden');
    greenAlert.innerHTML = "Payment successful";
}