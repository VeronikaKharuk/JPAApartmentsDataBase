package acad.prog.apartments;

import acad.prog.apartments.entities.Apartment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add apartment");
                    System.out.println("2: select apartment");
                    System.out.println("3: see all apartments");
                    System.out.println("4: delete apartment");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addApartment(sc);
                            break;
                        case "2":
                            System.out.println("1: select by district");
                            System.out.println("2: select by area");
                            System.out.println("3: select by the number of rooms");
                            System.out.println("4: select by price");
                            System.out.print("-> ");
                            selectApartment(sc);
                            break;
                        case "3":
                            viewApartments();
                            break;
                        case "4":
                            deleteApartment(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    static void addApartment(Scanner sc) {
        System.out.print("Enter district: ");
        String district = sc.nextLine();
        System.out.print("Enter address: ");
        String address = sc.nextLine();
        System.out.print("Enter area in square metres: ");
        String areaS = sc.nextLine();
        int area = Integer.parseInt(areaS);
        System.out.print("Enter the number of rooms: ");
        String roomNumberS = sc.nextLine();
        int roomNumber = Integer.parseInt(roomNumberS);
        System.out.print("Enter price in USD: ");
        String priceS = sc.nextLine();
        int price = Integer.parseInt(priceS);

        em.getTransaction().begin();
        try {
            Apartment a = new Apartment();
            a.setDistrict(district);
            a.setAddress(address);
            a.setArea(area);
            a.setRoomNumber(roomNumber);
            a.setPriceUSD(price);
            em.persist(a);
            em.getTransaction().commit();

            System.out.println(a.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteApartment(Scanner sc) {
        System.out.print("Enter apartment id: ");
        String aId = sc.nextLine();
        long id = Long.parseLong(aId);

        Apartment a = em.getReference(Apartment.class, id);
        if (a == null) {
            System.out.println("Apartment not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(a);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewApartments() {
        Query query = em.createQuery("SELECT a FROM Apartment a", Apartment.class);
        List<Apartment> list = (List<Apartment>) query.getResultList();

        for (Apartment a : list)
            System.out.println(a);
    }

    private static void selectApartment(Scanner sc) {
        String s = sc.nextLine();
        switch (s) {
            case "1":
                byDistrict(sc);
                break;
            case "2":
                byArea(sc);
                break;
            case "3":
                byRoomNumber(sc);
                break;
            case "4":
                byPrice(sc);
            default:
                return;
        }
    }

    private static void byPrice(Scanner sc) {
        System.out.print("Min price in USD: ");
        String minPriceS = sc.nextLine();
        int minPrice = Integer.parseInt(minPriceS);
        System.out.print("Max price in USD: ");
        String maxPriceS = sc.nextLine();
        int maxPrice = Integer.parseInt(maxPriceS);

        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.priceUSD > :minPrice AND a.priceUSD < :maxPrice", Apartment.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);

        List<Apartment> aList = (List<Apartment>) query.getResultList();

        for (Apartment a : aList) {
            System.out.println(a);
        }
    }

    private static void byRoomNumber(Scanner sc) {
        System.out.print("Enter the number of rooms: ");
        String roomNumberS = sc.nextLine();
        int roomNumber = Integer.parseInt(roomNumberS);
        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.roomNumber = :rnumber", Apartment.class);
        query.setParameter("rnumber", roomNumber);

        List<Apartment> aList = (List<Apartment>) query.getResultList();

        for (Apartment a : aList) {
            System.out.println(a);
        }
    }

    private static void byArea(Scanner sc) {
        System.out.print("Min area in square metres: ");
        String minAreaS = sc.nextLine();
        int minArea = Integer.parseInt(minAreaS);
        System.out.print("Max area in square metres: ");
        String maxAreaS = sc.nextLine();
        int maxArea = Integer.parseInt(maxAreaS);

        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.area > :minArea AND a.area < :maxArea", Apartment.class);
        query.setParameter("minArea", minArea);
        query.setParameter("maxArea", maxArea);

        List<Apartment> aList = (List<Apartment>) query.getResultList();

        for (Apartment a : aList) {
            System.out.println(a);
        }
    }

    private static void byDistrict(Scanner sc) {
        System.out.print("Enter district: ");
        String district = sc.nextLine();
        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.district = :district", Apartment.class);
        query.setParameter("district", district);

        List<Apartment> aList = (List<Apartment>) query.getResultList();

        for (Apartment a : aList) {
            System.out.println(a);
        }
    }


}