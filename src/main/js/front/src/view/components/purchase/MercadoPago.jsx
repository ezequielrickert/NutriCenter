import React, { useEffect } from 'react';

const MercadoPago = () => {
    useEffect(() => {
        const script = document.createElement('script');
        script.src = "https://sdk.mercadopago.com/js/v2";
        script.onload = () => {
            const mp = new window.MercadoPago('TEST-109ce7cf-50e1-4886-bcd7-75c7df1c7d59', {
                locale: 'es-AR'
            });

            mp.bricks().create("wallet", "wallet_container", {
                initialization: {
                    preferenceId: "<PREFERENCE_ID>",
                },
            });
        };
        document.head.appendChild(script);

        return () => {
            document.head.removeChild(script);
        };
    }, []);

    return (
        <div id="wallet_container"></div>
    );
};

export default MercadoPago;