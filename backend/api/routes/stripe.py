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
