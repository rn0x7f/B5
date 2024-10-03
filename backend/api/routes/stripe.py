import stripe
from fastapi import APIRouter, HTTPException
from schemas.schemas import PaymentRequest  # Importa el modelo desde tu archivo de schemas
from config.db import STRIPE_API_KEY

# Configurar la clave secreta de Stripe
stripe.api_key = STRIPE_API_KEY

# Definir un enrutador para Stripe
stripe_route = APIRouter()

# Ruta para crear un PaymentIntent
@stripe_route.post("/create-payment-intent")
async def create_payment_intent(payment_request: PaymentRequest):
    try:
        # Crear el PaymentIntent en Stripe
        payment_intent = stripe.PaymentIntent.create(
            amount=payment_request.amount,
            currency=payment_request.currency,
            description=payment_request.description,
            payment_method=payment_request.source,  # Utilizar el token o método de pago
            confirm=True  # Confirmar el pago automáticamente
        )

        return {
            "clientSecret": payment_intent["client_secret"],
            "status": payment_intent["status"]
        }
    except stripe.error.StripeError as e:
        # Manejar los errores de Stripe
        raise HTTPException(status_code=400, detail=str(e))

# Ruta para hacer un reembolso
@stripe_route.post("/refund/{payment_intent_id}")
async def refund_payment(payment_intent_id: str):
    try:
        # Realizar un reembolso utilizando el PaymentIntent
        refund = stripe.Refund.create(payment_intent=payment_intent_id)
        return {"status": refund["status"]}
    except stripe.error.StripeError as e:
        raise HTTPException(status_code=400, detail=str(e))

# Ruta para crear y configurar la Payment Sheet
@stripe_route.post("/payment-sheet")
async def create_payment_sheet(payment_request: PaymentRequest):
    try:
        # Crear un PaymentIntent en Stripe
        payment_intent = stripe.PaymentIntent.create(
            amount=payment_request.amount,  # Cantidad en centavos
            currency=payment_request.currency,  # Moneda, por ejemplo, "mxn"
            description=payment_request.description,
        )

        # Crear un Customer para asociar el método de pago (opcional)
        customer = stripe.Customer.create()

        # Crear una configuración para la Payment Sheet
        ephemeral_key = stripe.EphemeralKey.create(
            customer=customer["id"],
            stripe_version="2020-08-27"  # Asegúrate de usar la versión adecuada de la API de Stripe
        )

        return {
            "paymentIntent": payment_intent["client_secret"],
            "ephemeralKey": ephemeral_key.secret,
            "customer": customer["id"],
            "publishableKey": "tu_clave_publicable_de_stripe"
        }
    except stripe.error.StripeError as e:
        raise HTTPException(status_code=400, detail=str(e))
