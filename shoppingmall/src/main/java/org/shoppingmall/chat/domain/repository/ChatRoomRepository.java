package org.shoppingmall.chat.domain.repository;

import org.shoppingmall.chat.domain.ChatRoom;
import org.shoppingmall.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUser(User user);
}
