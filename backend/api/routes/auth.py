from fastapi import APIRouter

auth = APIRouter()

@auth.post("/signin")
async def signin():
    return {"message": "Sign in"}

@auth.post("/signup")
async def signup():
    return {"message": "Sign up"}
