package org.team.gstreet.fundboard.repository.search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.team.gstreet.fundboard.entity.FundBoard;
import org.team.gstreet.fundboard.entity.QFundBoard;

import java.util.List;

@Log4j2
public class FundBoardSearchImpl extends QuerydslRepositorySupport implements FundBoardSearch {

    public FundBoardSearchImpl() {

        super(FundBoard.class);
    }

    @Override
    public Page<FundBoard> search(char[] typeArr, String keyword, Pageable pageable) { //검색처리
        log.info("search1");

        QFundBoard fundBoard = QFundBoard.fundBoard;

        JPQLQuery<FundBoard> fundBoardJPQLQuery = from(fundBoard);

        if(typeArr != null && typeArr.length > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for (char type : typeArr) {
                if (type == 'T') {
                    builder.or(fundBoard.ftitle.contains(keyword));
                } else if (type == 'C') {
                    builder.or(fundBoard.fcontent.contains(keyword));
                } else if (type == 'W') {
                    builder.or(fundBoard.fwriter.contains(keyword));
                } else if(type == 'H'){
                    builder.or(fundBoard.ftags.contains(keyword));
                }
            }
            fundBoardJPQLQuery.where(builder);
        }

        fundBoardJPQLQuery.where(fundBoard.fno.gt(0L)); //bno > 0

        JPQLQuery<FundBoard> pagingQuery = this.getQuerydsl().applyPagination(pageable,fundBoardJPQLQuery);

        List<FundBoard> fundBoardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();

       return new PageImpl<>(fundBoardList,pageable,totalCount);
    }

//    @Override
//    public Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable) {
//        log.info("searchWithReplyCount");
//
////        select board.bno, board.title, board.writer, board.regDate, count(reply)
////        from Board board
////        left join Reply reply with reply.board = board
////        where board.bno = ?1
////        group by board
//
//        //1.EntityManager를 이용해서 Query
//        //2.getQuerydsl( )을 이용하는 방식
//
//        //Query를 만들때는 Q도메인 -- 값을 뽑을때는 엔티티 타입 /값
//        QFundBoard qBoard = QFundBoard.fundBoard;
//        QFundBoardReply qReply = QFundBoardReply.fundBoardReply;
//
//        JPQLQuery<FundBoard> query = from(qBoard);
//        query.leftJoin(qBoard.ftags);
//        query.leftJoin(qReply).on(qReply.fundboard.eq(qBoard));
//
//        query.groupBy(qBoard);
//
//        //검색조건이 있다면
//        if(typeArr != null && typeArr.length > 0){
//
//            BooleanBuilder condition = new BooleanBuilder();
//
//            for(char type: typeArr){
//                if(type == 'T'){
//                    condition.or(qBoard.ftitle.contains(keyword));
//                }else if(type =='C'){
//                    condition.or(qBoard.fcontent.contains(keyword));
//                }else if(type == 'W'){
//                    condition.or(qBoard.fwriter.contains(keyword));
//                }else if(type == 'H'){
//                    condition.or(qBoard.ftags.contains(keyword));
//                }
//            }
//            query.where(condition);
//        }
//
//        JPQLQuery<Tuple> selectQuery = query.select(qBoard.fno, qBoard.ftitle, qBoard.fwriter, qBoard.fcontent,
//                                                    qBoard.ftags.any(), qReply.count());
//
//        this.getQuerydsl().applyPagination(pageable, selectQuery);
//
//        List<Tuple> tupleList = selectQuery.fetch();
//        long freplyCnt = selectQuery.fetchCount();
//
//        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).
//                collect(Collectors.toList());
//
//        return new PageImpl<>(arr, pageable, freplyCnt);
//    }
}
