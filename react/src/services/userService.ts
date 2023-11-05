import { UserProfileUpdated, userRegister , AddressShipping } from "../model/UserModal";
const API_URL = import.meta.env.VITE_API_URL

export async function loginService(formLogin: URLSearchParams) {
  try {
    const response = await fetch(
      `${API_URL}/login`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          //'Access-Control-Allow-Origin':'*',
          // 'Access-Control-Allow-Methods':'POST,PATCH,OPTIONS'
        },
        body: formLogin.toString(),
      }
    );
    const data = await response.json();
    return data;
  } catch (e) {
    console.log(e);
  }
  return null;
}

export async function registerService(userRegister: userRegister) {
  try {
    const res = await fetch(
      `${API_URL}/api/user/auth/register`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userRegister),
      }
    );

    const data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function profileService() {
  try {
    const res = await fetch(
      `${API_URL}/api/user/profile`,
      {
        method: "GET",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    const data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function logoutService() {
  try {
    const res = await fetch(`${API_URL}/logout`, {
      method: "GET",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
    });

    const data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function updateProfileService(
  UserProfileUpdated: UserProfileUpdated
) {
  try {
    const res = await fetch(
      `${API_URL}/api/user/profile/basic`,
      {
        method: "PUT",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(UserProfileUpdated),
      }
    );

    const data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function verifyEmailService(email: string, code: string | null) {
  try {
    const res = await fetch(
      `${API_URL}/api/user/auth/verify-email`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body : JSON.stringify(code ? {email,code} : {email})
      }
    );

    const data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function verifyPhoneService(phone: string, code: string | null) {
  try {
    const res = await fetch(
      `${API_URL}/api/user/auth/verify-phone`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(code ? {phone,code} : {phone})
      }
    );

    const data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function forgetPasswordByMail(
  email: string,
  code?: string
): Promise<Response | null> {
  let requestBody: string;
  if (code == undefined) {
    requestBody = JSON.stringify({ email: email });
  } else {
    requestBody = JSON.stringify({ email: email, code: code });
  }
  try {
    const res = await fetch(
      `${API_URL}/api/user/auth/forgot-password-by-mail`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: requestBody
      }
    );
    return res;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function resetPassword(
  newPassword: string
): Promise<Response | null> {
  let requestParam = new URLSearchParams({ newPassword: newPassword });
  try {
    const res = await fetch(
      `http://localhost:8080/api/user/auth/reset-password?${requestParam.toString()}`,
      {
        method: "POST",
        credentials: "include",

        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
  newPassword: newPassword,
  confirmPassword: newPassword
})
      }
    );
    return res;
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function createAddressShip(
  addressShip : AddressShipping
) {
  try {
    const res = await fetch(
      `http://localhost:8080/api/user/address`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(addressShip),
      }
    );
    return await res.json();
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function getAddressShipsByUser(){
  try {
    const res = await fetch(
      `http://localhost:8080/api/user/address`,
      {
        method: "GET",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        }
      }
    );
    return await res.json();
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function updateAddressShip(
  addressShip : AddressShipping
){
  try {
    const res = await fetch(
      `http://localhost:8080/api/user/address/update`,
      {
        method: "PUT",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body : JSON.stringify(addressShip)
      }
    );
    return await res.json();
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function deleteAddressShip(
  idAddressShip : number
){
  try {
    const res = await fetch(
      `http://localhost:8080/api/user/address`,
      {
        method: "DELETE",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body : JSON.stringify({id : idAddressShip})
      }
    );
    return await res.json();
  } catch (error) {
    console.log(error);
  }
  return null;
}

export async function LogInWithGoogle(){
  try {
    const res = await fetch(
      `http://localhost:8080/login/oauth2/code/google/authorize`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        }
      }
    );
    return await res.json();
  } catch (error) {
    console.log(error);
  }
  return null;
}

