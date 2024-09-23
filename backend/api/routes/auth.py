from fastapi import APIRouter

auth = APIRouter()

@auth.post("/signin")
async def signin():
    return {"message": "Sign in"}