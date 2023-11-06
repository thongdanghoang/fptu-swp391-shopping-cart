const API_URL = import.meta.env.VITE_API_URL

export async function getOrdersByUserService() {
    try {
        const response = await fetch(
            `${API_URL}/api/orders/user`,
            {
                method: "GET",
                credentials: "include",
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.log(e);
    }
    return null;
}

export async function getDetailOrderService(orderId : string) {
    try {
        const response = await fetch(
            `${API_URL}/api/orders/${orderId}`,
            {
                method: "GET",
                credentials: "include",
            }
        );
        const data = await response.json();
        return data;
    } catch (e) {
        console.log(e);
    }
    return null;
}