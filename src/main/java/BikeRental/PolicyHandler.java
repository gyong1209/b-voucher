package BikeRental;

import BikeRental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    VoucherRepository voucherRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCancelled_RentalCancel(@Payload RentalCancelled rentalCancelled){

        if(rentalCancelled.isMe()){

            //voucher
            //voucherCnt 개수 조정 (+1)
            Voucher voucher = new Voucher();
            //voucher.setId(rentalCancelled.getVoucherId());
            voucher.setUserId(rentalCancelled.getUserId());
            voucher.setVoucherCnt(voucher.getVoucherCnt()+1);//1증가

            voucherRepository.save(voucher);

            System.out.println("##### listener RentalCancel : " + rentalCancelled.toJson());
        }
    }

}
