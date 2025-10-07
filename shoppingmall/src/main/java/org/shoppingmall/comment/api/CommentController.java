package org.shoppingmall.comment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shoppingmall.comment.api.dto.request.CommentSaveReqDto;
import org.shoppingmall.comment.api.dto.request.CommentUpdateReqDto;
import org.shoppingmall.comment.api.dto.response.CommentInfoResDto;
import org.shoppingmall.comment.api.dto.response.CommentListResDto;
import org.shoppingmall.comment.application.CommentService;
import org.shoppingmall.common.config.ApiResponseTemplate;
import org.shoppingmall.common.config.CommonApiResponse;
import org.shoppingmall.common.error.SuccessCode;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@Tag(name = "댓글 API", description = "댓글 CRUD API")
@CommonApiResponse
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 전체 조회", description = "로그인한 사용자가 상품 리뷰 한 개에 달린 전체 댓글 리스트를 조회합니다.")
    @GetMapping("/all")
    public ApiResponseTemplate<CommentListResDto> getCommentList(@RequestParam Long reviewId, Principal principal) {
        CommentListResDto commentListResDto = commentService.getCommentList(reviewId, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.GET_SUCCESS, commentListResDto);
    }

    @Operation(summary = "댓글 생성", description = "로그인한 사용자가 댓글을 생성합니다.\n" + "부모 댓글로 요청 보낼시에는 parentCommentId를 null로 보내면 됩니다.")
    @PostMapping
    public ApiResponseTemplate<CommentInfoResDto> saveComment(@RequestParam Long reviewId,
                                                         @Valid @RequestBody CommentSaveReqDto commentSaveReqDto,
                                                         Principal principal) {
        CommentInfoResDto commentInfoResDto = commentService.saveComment(reviewId, commentSaveReqDto, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.COMMENT_SAVE_SUCCESS, commentInfoResDto);
    }

    @Operation(summary = "댓글 수정", description = "로그인한 사용자가 댓글을 수정합니다.")
    @PutMapping
    public ApiResponseTemplate<CommentInfoResDto> updateComment(@RequestParam Long commentId,
                                                           @Valid @RequestBody CommentUpdateReqDto commentUpdateReqDto,
                                                           Principal principal) {
        CommentInfoResDto commentInfoResDto = commentService.updateComment(commentId, commentUpdateReqDto, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.COMMENT_UPDATE_SUCCESS, commentInfoResDto);
    }

    @Operation(summary = "댓글 삭제", description = "로그인한 사용자가 댓글을 삭제합니다.")
    @DeleteMapping
    public ApiResponseTemplate<Integer> deleteComment(@RequestParam Long commentId, Principal principal) {
        int commentCount = commentService.deleteComment(commentId, principal);
        return ApiResponseTemplate.successResponse(SuccessCode.COMMENT_DELETE_SUCCESS, commentCount);
    }
}
